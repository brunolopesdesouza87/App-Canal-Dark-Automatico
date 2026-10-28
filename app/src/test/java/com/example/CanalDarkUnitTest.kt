package com.example

import com.example.data.repository.AutomationRepository
import com.example.ui.MainViewModel
import com.example.ui.ScreenTab
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CanalDarkUnitTest {

    private lateinit var repo: AutomationRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        repo = AutomationRepository()
        viewModel = MainViewModel(automationRepo = repo)
    }

    @Test
    fun testNichesAvailable() {
        assertTrue(repo.niches.isNotEmpty())
        assertEquals("Mistérios & Fatos Ocultos", repo.niches[0].name)
    }

    @Test
    fun testGenerateDraftForNiche() {
        val draft = repo.generateDraftForNiche(repo.niches[0], "Área Secreta")
        assertNotNull(draft)
        assertTrue(draft.title.isNotBlank())
        assertTrue(draft.hook.isNotBlank())
        assertTrue(draft.script.isNotBlank())
        assertTrue(draft.visualPrompts.isNotEmpty())
        assertTrue(draft.tags.isNotEmpty())
    }

    @Test
    fun testRoiCalculation() {
        viewModel.updateVideosPerDay(3)
        val roi = viewModel.roi.value
        assertEquals(3, roi.videosPerDay)
        assertEquals(90, roi.monthlyVideos)
        assertEquals("2.7M", roi.estimatedMonthlyViews)
    }

    @Test
    fun testTabNavigation() {
        viewModel.setTab(ScreenTab.YOUTUBE_API)
        assertEquals(ScreenTab.YOUTUBE_API, viewModel.currentTab.value)
        viewModel.setTab(ScreenTab.FLOW_DELIVERY)
        assertEquals(ScreenTab.FLOW_DELIVERY, viewModel.currentTab.value)
    }

    @Test
    fun testLicenseUnlockLogic() {
        assertFalse(viewModel.isUnlocked.value)
        viewModel.updateLicenseInput("CAKTO-VIP-12345")
        viewModel.attemptUnlock()
        assertTrue(viewModel.isUnlocked.value)
        assertNull(viewModel.unlockError.value)
    }

    @Test
    fun testUrlsConfiguredProperly() {
        assertEquals("https://pay.cakto.com.br/38vofpw_1249382", repo.checkoutUrl)
        assertTrue(repo.demoVideoUrl.contains("iwxtDl8Jl3E"))
        assertTrue(repo.readyWorkflowJson.contains("n8n-nodes-base.youTube"))
    }
}
